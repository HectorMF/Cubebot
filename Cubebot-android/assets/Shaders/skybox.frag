#ifdef GL_ES
precision highp float;
#endif

varying vec3 v_texCoord;
uniform samplerCube s_cubemap;
void main ()
{
  gl_FragColor = textureCube (s_cubemap, v_texCoord);
}