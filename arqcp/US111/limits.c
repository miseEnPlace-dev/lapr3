#include <stdint.h>

#include "random.h"
#include "sensor.h"

extern uint64_t state;
extern uint64_t inc;

void reset_seed() {
    state = get_value_from_dev_random();
    inc = get_value_from_dev_random();
}

int get_total_errors(Sensor s) {
    int total = 0;
    // TODO
    /* for (int i = 0; i < size; i++) */
    /*     if (arr[i] == 1) */
    /*         total++; */
    return total;
}
