#include <stdio.h>

#include "sensor.h"
#include "shared.h"

#define COLUMNS 3

void get_summary_matrix(Sensor **data, unsigned int const *n_sensors, int result[][COLUMNS]) {
  // temperature
  int sum = 0;
  char max_temp = (char)data[0][0].readings[0];
  char min_temp = (char)data[0][0].readings[0];
  int count_regists = 0;

  for (int j = 0; j < n_sensors[0]; j++)
  {
    unsigned short *readings = data[0][j].readings;
    for (int k = 0; k < data[0][j].readings_size; k++)
    {
      sum += *(readings + k);

      if (*(readings + k) < min_temp)
        min_temp = *(readings + k);
      if (*(readings + k) > max_temp)
        max_temp = *(readings + k);

      count_regists++;
    }
  }

  *result[0] = min_temp;
  *(result[0] + 1) = max_temp;
  *(result[0] + 2) = sum / count_regists;

  // others
  for (int i = 1; i < NUM_OF_SENSOR_TYPES; i++) {
    sum = 0;
    unsigned short max = data[i][0].readings[0];
    unsigned short min = data[i][0].readings[0];
    count_regists = 0;

    for (int j = 0; j < n_sensors[i]; j++)
    {
      unsigned short *readings = data[i][j].readings;
      for (int k = 0; k < data[i][j].readings_size; k++)
      {
        sum += *(readings + k);

        if (*(readings + k) < min)
          min = *(readings + k);
        if (*(readings + k) > max)
          max = *(readings + k);

        count_regists++;
      }
    }

    *result[i] = min;
    *(result[i] + 1) = max;
    *(result[i] + 2) = sum / count_regists;
  }
}

void print_summary(Sensor **data, unsigned int const*n_sensors) {
  // US103
  int result[NUM_OF_SENSOR_TYPES][COLUMNS];

  get_summary_matrix(data, n_sensors, result);

  printf("\n  ---   |");
  for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++)
    printf(" %12s |", SENSOR_TYPE_DESIGNATIONS[i]);

  printf("\n--------+");
  for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++)
    printf("--------------+");

  printf("\nMin     |");
  for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++)
    printf(" %12d |", **(result + i));

  printf("\nMax     |");
  for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++)
    printf(" %12d |", *(*(result + i) + 1));

  printf("\nAverage |");
  for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++)
    printf(" %12d |", *(*(result + i) + 2));

  printf("\n");
}

