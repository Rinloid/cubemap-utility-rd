$input a_color0, a_position, a_texcoord0
#ifdef INSTANCING
    $input i_data0, i_data1, i_data2
#endif
$output v_color0, v_texcoord0

#include <../../include/bgfx_compute.sh>

uniform vec4 ViewPositionAndTime;

void main() {
    mat4 model;
#ifdef INSTANCING
    model = mtxFromCols(i_data0, i_data1, i_data2, vec4(0, 0, 0, 1));
#else
    model = u_model[0];
#endif

    vec3 worldPos = mul(model, vec4(a_position, 1.0)).xyz;
    vec4 color;
#ifdef RENDER_AS_BILLBOARDS
    worldPos += vec3(0.5, 0.5, 0.5);
    vec3 viewDir = normalize(worldPos - ViewPositionAndTime.xyz);
    vec3 boardPlane = normalize(vec3(viewDir.z, 0.0, -viewDir.x));
    worldPos = (worldPos -
        ((((viewDir.yzx * boardPlane.zxy) - (viewDir.zxy * boardPlane.yzx)) *
        (a_color0.z - 0.5)) +
        (boardPlane * (a_color0.x - 0.5))));
    color = vec4(1.0, 1.0, 1.0, 1.0);
#else
    color = a_color0;
#endif

#ifdef TRANSPARENT
    if (a_color0.r != a_color0.g || a_color0.g != a_color0.b || a_color0.r != a_color0.b) {
        color = vec4(0.0, 0.0, 0.0, 0.0);
    }
#endif

    v_texcoord0 = a_texcoord0;
    v_color0 = color;

    gl_Position = mul(u_viewProj, vec4(worldPos, 1.0));
}
