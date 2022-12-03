#ifndef LIMIT_H
#define LIMIT_H

#define UPPER_LIMIT_TEMPERATURE 20
#define LOWER_LIMIT_TEMPERATURE 0
#define UPPER_LIMIT_VELOCITY 30
#define LOWER_LIMIT_VELOCITY 0
#define UPPER_LIMIT_DIR_WIND 359
#define LOWER_LIMIT_DIR_WIND 0
#define UPPER_LIMIT_PLUVIO 30
#define LOWER_LIMIT_PLUVIO 0
#define UPPER_LIMIT_SOIL_HUMIDITY 100
#define LOWER_LIMIT_SOIL_HUMIDITY 0
#define UPPER_LIMIT_AIR_HUMIDITY 100
#define LOWER_LIMIT_AIR_HUMIDITY 0

#define MAX_INCORRECT_READS 3

void reset_seed();

int get_total_errors(char *arr, int size);

#endif