#ifndef SENSOR_H
#define SENSOR_H
typedef struct
{
  char *name;                   // nome do sensor
  char *units;                  // unidades das leituras
  unsigned long frequency;      // frequência de leituras (em segundos)
  unsigned long readings_size;  // segundos dia / frequência (tamanho do array readings)
  unsigned short *readings;     // array de leituras diárias
  unsigned char *errors;        // array de erros das leituras
  unsigned short id;
  unsigned short max_limit;     // limites do sensor
  unsigned short min_limit; 
  unsigned char sensor_type;
} Sensor;

// original:
// ***. .... **** **** **** .... **** **** **** **** **** **** **** **** **** ****
// 64 bytes

// optimized:
// 56 bytes

#endif

