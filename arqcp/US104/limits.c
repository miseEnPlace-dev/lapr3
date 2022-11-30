#include <stdint.h>

#include "random.h"

void reset_seed(uint64_t *state, uint64_t *inc) {
    *state = get_value_from_dev_random();
    *inc = get_value_from_dev_random();
}

char limit(short value) {
  return 0;
}
