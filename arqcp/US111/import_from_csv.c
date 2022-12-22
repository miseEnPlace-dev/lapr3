#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "sensor.h"
#include "shared.h"

#define BUFFER_SIZE 1024

// Reads the file and stores the number of sensors and frequency in the arrays
void read_file(char *filename, unsigned int *n_sensors, unsigned int *f_sensors)
{
  char buf[BUFFER_SIZE];

  FILE *fp = fopen(filename, "r");
  if (fp == NULL)
  {
    printf("Error opening file!");
    exit(1);
  }

  // Read each line of the file
  int i = 0;
  while (fgets(buf, BUFFER_SIZE, fp) != NULL && i < NUM_OF_SENSOR_TYPES)
  {

      // Parse the line using strtok()
      char *token = strtok(buf, ",");
      token = strtok(NULL, ",");

      // Convert the second column to an unsigned int and store it in the array
      n_sensors[i] = atoi(token);

      // Convert the third column to an unsigned int and store it in the array
      token = strtok(NULL, ",");
      f_sensors[i] = atoi(token);
      i++;
  }

  fclose(fp);
}
