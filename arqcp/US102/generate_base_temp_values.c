#include <stdio.h>
#include <math.h>
#include <stdint.h>

#include "random.h"

extern state;
extern inc;
// This function will generate a sinusoid like set of values
void generate_base_temp_values(char* arr, int num)
{
  state = get_value_from_dev_random();
  inc = get_value_from_dev_random();

  double division = M_PI / num;
  double const STRETCH_FATOR_RND = ((double)(pcg32_random_r() % 100) / 100)+1;
  int const BASE_TEMP_VALUE = 10;
  int const TEMP_VARIATION = 20;

  for (int i = 0; i < num; i++) {
    double angle = STRETCH_FATOR_RND*((double)i*division);
    char result = BASE_TEMP_VALUE+(char)(sin(angle)*TEMP_VARIATION);
    if(result < 0) result = -result;
    *(arr+i) = result;
  }

}
