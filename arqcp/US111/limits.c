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
    unsigned char const *arr = s.errors;
    unsigned int size = s.readings_size;

    int total = 0;
    for (int i = 0; i < size; i++)
        if (arr[i] == 1)
            total++;
    return total;
}

unsigned char exceeded_limits_signed(unsigned int index, Sensor s) {
    if ((char)s.readings[index] > s.max_limit || (char)s.readings[index] < s.min_limit)
        return 1;
    return 0;
}

unsigned char exceeded_limits(unsigned int index, Sensor s) {
    if (s.readings[index] > s.max_limit || s.readings[index] < s.min_limit)
        return 1;
    return 0;
}

