$input v_texcoord0
#include <../../include/bgfx_compute.sh>
SAMPLER2D_AUTOREG(s_BlitTexture);
void main() {
    gl_FragColor = texture2D(s_BlitTexture, v_texcoord0);
}