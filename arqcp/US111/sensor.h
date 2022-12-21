#ifndef SENSOR_H
#define SENSOR_H
typedef struct
{
  char *name;                   // nome do sensor
  char *units;
  unsigned long frequency;      // frequency de leituras (em segundos)
  unsigned long readings_size;  // tamanho do array de leituras
  unsigned short *readings;     // array de leituras diárias
  unsigned char *errors;        // array de erros das leituras
  unsigned short id;
  unsigned short max_limit;     // limites do sensor
  unsigned short min_limit; 
  unsigned char sensor_type;
} Sensor;

// original:
// ***. .... **** **** **** .... **** **** **** **** **** **** **** **** **** ****

// optimized:
// 

#endif

