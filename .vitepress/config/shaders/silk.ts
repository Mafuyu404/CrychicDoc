import { baseVertexShader, buildTemplate } from "./templates/base-shader";

export const silkShader = buildTemplate({
    key: "silk",
    vertex: baseVertexShader,
    fragment: `
uniform float uTime;
uniform vec2 uResolution;
uniform vec3 uColor1;
uniform vec3 uColor2;
uniform vec3 uColor3;
uniform float uThemeIsDark;
varying vec2 vUv;

// High frequency noise for fabric structure
float noise(vec2 p) {
    return smoothstep(-0.5, 0.9, sin((p.x - p.y) * 555.0) * sin(p.y * 1444.0)) - 0.4;
}

// Layered noise for fiber details
float fabric(vec2 p) {
    mat2 m = mat2(1.6, 1.2, -1.2, 1.6);
    float f = 0.4 * noise(p);
    p = m * p; f += 0.3 * noise(p);
    p = m * p; f += 0.2 * noise(p);
    return f + 0.1 * noise(m * p);
}

// Macro fold structure with micro texture integration
float silk(vec2 uv, float t) {
    float s = sin(5.0 * (uv.x + uv.y + cos(2.0 * uv.x + 5.0 * uv.y)) + sin(12.0 * (uv.x + uv.y)) - t);
    s = 0.7 + 0.3 * (s * s * 0.5 + s);
    // Use screen resolution to scale the fabric noise so it remains crisp and clear
    s *= 0.9 + 0.6 * fabric(uv * min(uResolution.x, uResolution.y) * 0.0006);
    return s * 0.9 + 0.1;
}

// Derivative approximation for lighting and highlights
float silkd(vec2 uv, float t) {
    float xy = uv.x + uv.y;
    float d = (5.0 * (1.0 - 2.0 * sin(2.0 * uv.x + 5.0 * uv.y)) + 12.0 * cos(12.0 * xy)) * cos(5.0 * (cos(2.0 * uv.x + 5.0 * uv.y) + xy) + sin(12.0 * xy) - t);
    return 0.005 * d * (sign(d) + 3.0);
}

// ACES Tone mapping for cinematic grading
vec3 acesFilm(const vec3 x) {
    const float a = 2.51;
    const float b = 0.03;
    const float c = 2.43;
    const float d = 0.59;
    const float e = 0.14;
    return clamp((x * (a * x + b)) / (x * (c * x + d) + e), 0.0, 1.0);
}

void main() {
    float mr = min(uResolution.x, uResolution.y);
    vec2 fragCoord = vUv * uResolution;
    vec2 uv = fragCoord / mr;
    
    // Time scaling for smooth movement
    float t = uTime * 0.3;
    uv.y += 0.03 * sin(8.0 * uv.x - t);
    
    float s = sqrt(max(0.0, silk(uv, t)));
    float d = silkd(uv, t);
	
    // Interpolate base colors
    vec3 baseCol = mix(uColor1, uColor2, s);
    
    // Dynamic shadowing based on theme
    float shadowMask = mix(mix(0.85, 1.0, s), s, uThemeIsDark);
    vec3 c = baseCol * shadowMask;
    
    // Specular highlights and shadowing from geometric derivative
    c += mix(0.4, 0.7, uThemeIsDark) * uColor3 * d;
    c *= 1.0 - max(0.0, 0.8 * d);
    
    // Grading operations
    vec2 centeredUv = (fragCoord - 0.5 * uResolution.xy) / uResolution.y;
    
    // Apply contrast logic based on dot(uv, uv)
    c = pow(c, vec3(1.05, 1.0, 0.9 + dot(centeredUv, centeredUv) * 0.05));
    
    // Tone mapping
    c = mix(acesFilm(c * 1.15), c, 0.15);
    
    // Vignette
    c = mix(c, c * c * 0.5, dot(centeredUv, centeredUv) * 0.5);
    
    // Final polish contrast and brightness
    c = mix(c, smoothstep(0.0, 1.0, c), mix(0.15, 0.35, uThemeIsDark));

    gl_FragColor = vec4(clamp(c, 0.0, 1.0), 1.0);
}
`,
    defaultUniforms: {
        uResolution: { type: "vec2", value: [1.0, 1.0] },
        uColor1: {
            type: "vec3",
            value: {
                light: [0.96, 0.97, 0.98],
                dark: [0.06, 0.08, 0.14],
            },
        },
        uColor2: {
            type: "vec3",
            value: {
                light: [0.98, 0.99, 1.0],
                dark: [0.1, 0.13, 0.2],
            },
        },
        uColor3: {
            type: "vec3",
            value: {
                light: [0.92, 0.94, 0.97],
                dark: [0.22, 0.3, 0.48], // used for highlight color
            },
        },
    },
});
