$input a_position
#include <../../include/bgfx_compute.sh>
void main() {
    gl_Position = mul(u_viewProj, vec4(a_position, 1.0));
}
