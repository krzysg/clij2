
__kernel void minimum_distance_of_touching_neighbors (
    IMAGE_src_distance_matrix_TYPE src_distance_matrix,
    IMAGE_src_touch_matrix_TYPE src_touch_matrix,
    IMAGE_dst_minimum_distance_list_TYPE dst_minimum_distance_list
) {
  const sampler_t sampler = CLK_NORMALIZED_COORDS_FALSE | CLK_ADDRESS_CLAMP_TO_EDGE | CLK_FILTER_NEAREST;

  const int label_id = get_global_id(0);
  const int label_count = get_global_size(0);

  float minimum = 0;

  int y = label_id;
  int x = 0;
  for (x = 0; x < label_id; x++) {
    POS_src_touch_matrix_TYPE pos = POS_src_touch_matrix_INSTANCE(x, y, 0, 0);
    float value = READ_src_touch_matrix_IMAGE(src_touch_matrix, sampler, pos).x;
    if (value > 0) {
      if (minimum > value || minimum == 0) {
        minimum = READ_src_distance_matrix_IMAGE(src_distance_matrix, sampler, pos).x;
      }
    }
  }
  x = label_id;
  for (y = label_id + 1; y < label_count; y++) {
    POS_src_touch_matrix_TYPE pos = POS_src_touch_matrix_INSTANCE(x, y, 0, 0);
    float value = READ_src_touch_matrix_IMAGE(src_touch_matrix, sampler, pos).x;
    if (value > 0) {
      if (minimum > value || minimum == 0) {
        minimum = READ_src_distance_matrix_IMAGE(src_distance_matrix, sampler, pos).x;
      }
    }
  }

  WRITE_dst_minimum_distance_list_IMAGE(dst_minimum_distance_list, (POS_dst_minimum_distance_list_INSTANCE(label_id, 0, 0, 0)), CONVERT_dst_minimum_distance_list_PIXEL_TYPE(minimum));
}

