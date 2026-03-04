import { baseVertexShader, buildTemplate } from "./templates/base-shader";

export const silkShader = buildTemplate({
    key: "silk",
    vertex: baseVertexShader,
    fragment: `
uniform float uTime;
uniform vec2 uResolution;
uniform vec3 uBgColor;
uniform vec3 uColor1;
uniform vec3 uColor2;
uniform vec3 uColor3;
uniform float uThemeIsDark;
varying vec2 vUv;

// ── noise ──
float hash21(vec2 p) {
  p = fract(p * vec2(123.34, 456.21));
  p += dot(p, p + 31.79);
  return fract(p.x * p.y);
}

float noise(vec2 p) {
  vec2 i = floor(p);
  vec2 f = fract(p);
  float a = hash21(i);
  float b = hash21(i + vec2(1.0, 0.0));
  float c = hash21(i + vec2(0.0, 1.0));
  float d = hash21(i + vec2(1.0, 1.0));
  vec2 u = f * f * (3.0 - 2.0 * f);
  return mix(mix(a, b, u.x), mix(c, d, u.x), u.y);
}

float fbm(vec2 p) {
  float sum = 0.0;
  float amp = 0.5;
  mat2 m = mat2(1.6, 1.2, -1.2, 1.6);
  for (int i = 0; i < 4; i++) {
    sum += amp * noise(p);
    p = m * p + vec2(0.07, -0.06);
    amp *= 0.5;
  }
  return sum;
}

// Map screen UV to ratio-corrected UV to prevent stretching on mobile
vec2 getRatioUV() {
  vec2 uv = vUv;
  float aspect = uResolution.x / uResolution.y;
  // Center keeping 0.5 at the middle, expanding the shorter axis
  if (aspect > 1.0) {
    uv.x = (uv.x - 0.5) * aspect + 0.5;
  } else {
    uv.y = (uv.y - 0.5) / aspect + 0.5;
  }
  return uv;
}

// Flowing field for silk
// Produces smoother, larger folds by using lower frequency multipliers
float silkHeight(vec2 uv, float t) {
  // Gentle base displacement
  float flow = fbm(uv * vec2(1.5, 1.2) + vec2(t * 0.4, -t * 0.2));

  // Much softer, lower-frequency sine waves
  float w1 = sin((uv.y * 5.0 + flow * 2.5) - t * 0.8) * 1.0;
  float w2 = sin((uv.x * 4.0 - flow * 2.0) + t * 0.6) * 0.8;
  float w3 = sin(((uv.x + uv.y * 1.2) * 3.5 + flow * 1.8) - t * 0.4) * 0.6;
  
  return (w1 + w2 + w3) * 0.18; // Flatter overall geometry for smooth satin
}

// Calculate normal from height field
vec3 silkNormal(vec2 uv, float t) {
  float eps = 0.01; // slightly larger epsilon for softer normals
  float h  = silkHeight(uv, t);
  float hx = silkHeight(uv + vec2(eps, 0.0), t);
  float hy = silkHeight(uv + vec2(0.0, eps), t);
  vec3 n = normalize(vec3(-(hx - h) / eps, -(hy - h) / eps, 1.0));
  return n;
}

// ── LIGHT: bright white satin, softer shadows ──
vec3 renderLight(vec2 uv, float t) {
  vec3 normal = silkNormal(uv, t);

  vec3 lightDir = normalize(vec3(-0.3, -0.5, 1.0));
  vec3 viewDir = vec3(0.0, 0.0, 1.0);
  vec3 halfDir = normalize(lightDir + viewDir);

  // Very gentle diffuse shading (less shadow contrast)
  float diff = max(dot(normal, lightDir), 0.0);
  diff = mix(0.85, 1.0, diff); 

  // Softer specular highlight for a broader, less sharp sheen
  float spec = pow(max(dot(normal, halfDir), 0.0), 16.0); // lower shininess

  vec3 base = mix(uColor1, uColor2, vUv.y * 0.3); // use vUv for gradient to keep it screen-space
  vec3 col = base * diff;
  col += vec3(1.0) * spec * 0.18; // less intense specular

  // Softest touch of shadow
  col = mix(col, uColor3, (1.0 - diff) * 0.04);

  // Soft vignette
  float vig = 1.0 - smoothstep(0.4, 1.2, length(vUv - 0.5) * 1.2);
  col = mix(col * 0.98, col, vig);

  return clamp(col, 0.0, 1.0);
}

// ── DARK: smooth deep wave, minimal harsh gleam ──
vec3 renderDark(vec2 uv, float t) {
  vec3 normal = silkNormal(uv, t);

  vec3 lightDir = normalize(vec3(0.3, -0.4, 1.0));
  vec3 viewDir = vec3(0.0, 0.0, 1.0);
  vec3 halfDir = normalize(lightDir + viewDir);

  // Diffuse
  float diff = max(dot(normal, lightDir), 0.0);
  diff = mix(0.75, 1.0, diff);

  // Specular — much softer than before
  float spec = pow(max(dot(normal, halfDir), 0.0), 24.0);

  // Subtle secondary light
  vec3 lightDir2 = normalize(vec3(-0.4, 0.2, 1.0));
  vec3 halfDir2 = normalize(lightDir2 + viewDir);
  float spec2 = pow(max(dot(normal, halfDir2), 0.0), 32.0);

  vec3 base = mix(uColor1, uColor2, vUv.y * 0.4);
  vec3 col = base * diff;
  
  col += uColor3 * spec * 0.25; 
  col += uColor3 * spec2 * 0.12; 

  // Dark vignette
  float vig = 1.0 - smoothstep(0.4, 1.1, length(vUv - 0.5) * 1.1);
  col = mix(col * 0.85, col, vig);

  return clamp(col, 0.0, 1.0);
}

void main() {
  vec2 uv = getRatioUV(); // Scale coordinates by screen aspect ratio
  float t = uTime * 0.03; // Slower, more elegant movement
  float dark = clamp(uThemeIsDark, 0.0, 1.0);

  vec3 lightResult = renderLight(uv, t);
  vec3 darkResult  = renderDark(uv, t);

  vec3 col = mix(lightResult, darkResult, dark);

  gl_FragColor = vec4(col, 1.0);
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
