#ifdef GL_ES
precision highp float;
#endif

varying vec3 v_texCoord;
uniform mat4 u_mvpMatrix;
attribute vec4 a_position;
void main ()
{
  v_texCoord = a_position.xyz;
  gl_Position = (u_mvpMatrix * a_position);
}
