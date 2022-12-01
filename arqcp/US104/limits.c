#include <stdint.h>

#include "random.h"

extern uint64_t state;
extern uint64_t inc;

void reset_seed() {
    state = get_value_from_dev_random();
    inc = get_value_from_dev_random();
}

char limit(short value) {
  return 0;
}
