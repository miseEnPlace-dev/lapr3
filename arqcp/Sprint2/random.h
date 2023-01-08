#ifndef RANDOM_H
#define RANDOM_H
#include <stdint.h>
uint32_t pcg32_random_r();
uint64_t get_value_from_dev_random();
#endif
