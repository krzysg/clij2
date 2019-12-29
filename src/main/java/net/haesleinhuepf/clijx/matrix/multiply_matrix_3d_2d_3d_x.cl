
__kernel void multiply_matrix(
    IMAGE_dst_matrix_TYPE dst_matrix,
    IMAGE_src1_TYPE src1,
    IMAGE_src2_TYPE src2
) {
  const sampler_t sampler = CLK_NORMALIZED_COORDS_FALSE | CLK_ADDRESS_CLAMP_TO_EDGE | CLK_FILTER_NEAREST;

  const int dx = get_global_id(0);
  const int dy = get_global_id(1);

  int n_x = GET_IMAGE_WIDTH(src1);

  float sum = 0;
  for (int i = 0; i < n_x; i ++) {
      int4 pos1 = (int4){i, dy,0,0};
      int2 pos2 = (int2){dx, i};
      sum = sum + READ_src1_IMAGE(src1, sampler, pos1).x * READ_src2_IMAGE(src2, sampler, pos2).x;
  }
  float out = sum;
  int4 pos = (int4){dx, dy, 0, 0};
  WRITE_dst_matrix_IMAGE(dst_matrix, pos, CONVERT_dst_matrix_PIXEL_TYPE(out));

}