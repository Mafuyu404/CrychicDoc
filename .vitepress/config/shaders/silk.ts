import { baseVertexShader, buildTemplate } from "./templates/base-shader";

export const silkShader = buildTemplate({
    key: "silk",
    vertex: baseVertexShader,
    fragment: `
uniform float uTime;
uniform vec2 uResolution;
uniform float uThemeIsDark;
varying vec2 vUv;

float colormap_red(float x) {
    if (x < 0.9) {
        return 54.0 / 255.0;
    } else if (x < 20049.0 / 82979.0) {
        return (829.79 * x + 54.51) / 255.0;
    } else {
        return 1.0;
    }
}

float colormap_green(float x) {
    if (x < 20049.0 / 82979.0) {
        return 0.0;
    } else if (x < 327013.0 / 810990.0) {
        return (8546482679670.0 / 10875673217.0 * x - 2064961390770.0 / 10875673217.0) / 255.0;
    } else if (x <= 1.0) {
        return (103806720.0 / 483977.0 * x + 19607415.0 / 483977.0) / 255.0;
    } else {
        return 1.0;
    }
}

float colormap_blue(float x) {
    if (x < 0.0) {
        return 54.0 / 255.0;
    } else if (x < 7249.0 / 82979.0) {
        return (829.79 * x + 54.51) / 255.0;
    } else if (x < 20049.0 / 82979.0) {
        return 127.0 / 255.0;
    } else if (x < 327013.0 / 810990.0) {
        return (792.02249341361393720147485376583 * x - 64.364790735602331034989206222672) / 255.0;
    } else {
        return 1.0;
    }
}

vec4 colormap(float x) {
    return vec4(colormap_red(x), colormap_green(x), colormap_blue(x), 1.0);
}

float rand(vec2 n) { 
    return fract(sin(dot(n, vec2(12.9898, 4.1414))) * 43758.5453);
}

float noise(vec2 p) {
    vec2 ip = floor(p);
    vec2 u = fract(p);
    u = u * u * (3.0 - 2.0 * u);

    float res = mix(
        mix(rand(ip), rand(ip + vec2(1.0, 0.0)), u.x),
        mix(rand(ip + vec2(0.0, 1.0)), rand(ip + vec2(1.0, 1.0)), u.x),
        u.y
    );
    return res * res;
}

const mat2 mtx = mat2(0.80, 0.60, -0.60, 0.80);

float fbm(vec2 p) {
    float t = uTime * 0.18;
    float f = 0.0;

    f += 0.500000 * noise(p + t); p = mtx * p * 2.02;
    f += 0.031250 * noise(p); p = mtx * p * 2.01;
    f += 0.250000 * noise(p); p = mtx * p * 2.03;
    f += 0.125000 * noise(p); p = mtx * p * 2.01;
    f += 0.062500 * noise(p); p = mtx * p * 2.04;
    f += 0.015625 * noise(p + sin(t));

    return f / 0.96875;
}

float pattern(in vec2 p) {
    return fbm(p + fbm(p + fbm(p)));
}

void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 uv = fragCoord / uResolution.x * 1.45;
    float rawShade = pattern(uv);
    float shade = clamp(smoothstep(0.08, 0.92, pow(rawShade, 0.68) * 1.12), 0.0, 1.0);
    vec3 color = colormap(shade).rgb;
    float lightMode = 1.0 - uThemeIsDark;
    float cloudMass = fbm(uv * 0.72 + vec2(-0.12, 0.08));
    float cloudSoft = smoothstep(0.16, 0.78, mix(shade, cloudMass, 0.42));
    float density = max(smoothstep(0.18, 0.58, cloudSoft), smoothstep(0.48, 0.86, shade) * 0.68);
    float rim = smoothstep(0.46, 0.82, shade) * (1.0 - smoothstep(0.78, 0.96, cloudMass));
    float glow = smoothstep(0.50, 0.96, shade);
    float ember = smoothstep(0.32, 0.72, cloudSoft) * (1.0 - smoothstep(0.88, 1.0, shade));
    vec3 upperSky = vec3(0.58, 0.48, 0.68);
    vec3 lowerSky = vec3(0.98, 0.50, 0.34);
    vec3 sky = mix(lowerSky, upperSky, smoothstep(0.05, 0.95, vUv.y));
    vec3 violetShadow = vec3(0.58, 0.47, 0.62);
    vec3 peachCloud = vec3(1.0, 0.62, 0.48);
    vec3 coralBelly = vec3(0.98, 0.36, 0.38);
    vec3 sunGold = vec3(1.0, 0.72, 0.34);
    vec3 warmWhite = vec3(1.0, 0.96, 0.86);
    vec3 cloudColor = mix(violetShadow, peachCloud, cloudSoft);
    cloudColor = mix(cloudColor, coralBelly, ember * 0.26);
    cloudColor = mix(cloudColor, sunGold, rim * 0.44);
    cloudColor = mix(cloudColor, warmWhite, glow * 0.58);
    cloudColor += vec3(0.08, 0.04, 0.00) * pow(cloudSoft, 2.0);
    vec3 lightColor = mix(sky, cloudColor, density);
    color = mix(color, clamp(lightColor, 0.0, 1.0), lightMode);
    float alpha = mix(0.94, shade, uThemeIsDark);
    fragColor = vec4(color, alpha);
}

void main() {
    mainImage(gl_FragColor, vUv * uResolution.xy);
}
`,
    defaultUniforms: {
        uResolution: { type: "vec2", value: [1.0, 1.0] },
    },
});
