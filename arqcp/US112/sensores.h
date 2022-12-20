#ifndef SENSORS_H
#define SENSORS_H

#define TEMPERATURES_SENSOR_INTERVAL 2000
#define VELOCITY_SENSOR_INTERVAL 10000
#define DIRECTION_SENSOR_INTERVAL 10000
#define PLUVIO_SENSOR_INTERVAL 10000
#define SOIL_HUMIDITY_SENSOR_INTERVAL 10000
#define AIR_HUMIDITY_SENSOR_INTERVAL 10000

const char TEMP_SENSOR_MAX_VARIATION = 2;
const char TEMP_BASE_VALUE = 10;
const char VELC_SENSOR_MAX_VARIATION = 10; // velocity variation (km/h)
const char PLUV_CONTRIB_HUMD = 8;          // every mm of pluv contributes to humidity (%)
const char SOIL_HUMD_SENSOR_MAX_VARIATION = 3;
const char SOIL_HUMD_SENSOR_RAINING_MAX_VARIATION = 20;
const char VELC_SENSOR_DIR_WIND_MAX_VARIATION = 10;
const char PLUVIO_SENSOR_MAX_VARIATION = 5;
const char HIGH_TEMP_DEFAULT = 25;
const char PLUVIO_SENSOR_MAX_VARIATION_HIGH_TEMP = 2;

#define N_OF_TEMP_SENSORS 3
#define N_OF_VELOCITY_SENSORS 3
#define N_OF_DIRECTION_SENSORS 3
#define N_OF_PLUVIO_SENSORS 3
#define N_OF_SOIL_HUMIDITY_SENSORS 3
#define N_OF_AIR_HUMIDITY_SENSORS 3

#define TEMPERATURE_SENSORS_INDEX 0
#define VELOCITY_SENSORS_INDEX 1
#define DIR_WIND_SENSORS_INDEX 2
#define PLUVIO_SENSORS_INDEX 3
#define SOIL_HUMIDITY_SENSORS_INDEX 4
#define AIR_HUMIDITY_SENSORS_INDEX 5

extern int ***matrix;

#include <stdint.h>
uint32_t pcg32_random_r();
uint64_t get_value_from_dev_random();

/**
 * Gera o valor de temperatura com base no último valor de temperatura.
 * O novo valor a gerar será o incremento ao último valor gerado, adicionado de um valor
 * aleatório (positivo ou negativo).
 *
 * A componente aleatória não deverá produzir variações drásticas à temperatura entre medições consecutivas.
 *
 * @param ult_temp Último valor de temperatura medido (°C)
 * @param comp_rand Componente aleatório para a geração do novo valor da temperatura
 *
 * @return A nova medição do valor da temperatura (°C)
 */
char sens_temp(char ult_temp, char comp_rand);

/**
 * Gera o valor de velocidade do vento com base no último valor de velocidade do vento.
 * O novo valor a gerar será o incremento ao último valor gerado, adicionado de um valor
 * aleatório (positivo ou negativo).
 *
 * A componente aleatória pode produzir variações altas entre medições consecutivas, simulando assim o efeito
 * de rajadas de vento.
 *
 * @param ult_velc_vento Última velocidade do vento medida (km/h)
 * @param comp_rand Componente aleatório para a geração do novo valor da velocidade do vento
 *
 * @return O novo medição do valor da velocidade do vento (km/h)
 */
unsigned char sens_velc_vento(unsigned char ult_velc_vento, char comp_rand);

/**
 * Gera o valor de direção do vento com base no último valor de direção do vento.
 * O novo valor a gerar será o incremento ao último valor gerado, adicionado de um valor
 * aleatório (positivo ou negativo).
 *
 * A direção do vento toma valores de 0 a 359, representam graus relativamente ao Norte.
 *
 * A direção do vento não deve variar de forma drástica entre medições consecutivas.
 *
 * @param ult_dir_vento Última direção do vento medida (graus)
 * @param comp_rand Componente aleatório para a geração do novo valor da direção do vento
 *
 * @return A nova medição do valor da direção do vento (graus)
 */
unsigned short sens_dir_vento(unsigned short ult_dir_vento, short comp_rand);

/**
 * Gera o valor de humidade atmosférica com base no último valor de humidade atmosférica.
 * O novo valor a gerar será o incremento ao último valor gerado, adicionado de um valor
 * de modificação (positivo ou negativo).
 *
 * O valor de modificação terá uma componente aleatória e uma componente relativa ao último
 * valor de pluvisiodade registado, que contribuirá para uma maior ou menor alteração à
 * modificação.
 *
 * A menos que tenha chovido, o valor de modificação não deverá produzir variações drásticas à humidade
 * atmosférica entre medições consecutivas.
 *
 * @param ult_hmd_atm Última humidade atmosférica medida (percentagem)
 * @param ult_pluvio Último valor de pluviosidade medido (mm)
 * @param comp_rand Componente aleatório para a geração do novo valor da humidade atmosférica
 *
 * @return A nova medição do valor da humidade atmosférica (percentagem)
 */
unsigned char sens_humd_atm(unsigned char ult_hmd_atm, unsigned char ult_pluvio, char comp_rand);

/**
 * Gera o valor de humidade do solo com base no último valor de humidade do solo.
 * O novo valor a gerar será o incremento ao último valor gerado, adicionado de um valor
 * de modificação (positivo ou negativo).
 *
 * O valor de modificação terá uma componente aleatória e uma componente relativa ao último
 * valor de pluvisiodade registado, que contribuirá para uma maior ou menor alteração à
 * modificação.
 *
 * A menos que tenha chovido, o valor de modificação não deverá produzir variações drásticas à humidade do
 * solo entre medições consecutivas.
 *
 * @param ult_hmd_solo Última humidade do solo medida (percentagem)
 * @param ult_pluvio Último valor de pluviosidade medido (mm)
 * @param comp_rand Componente aleatório para a geração do novo valor da humidade do solo
 *
 * @return A nova medição do valor da humidade do solo (percentagem)
 */
unsigned char sens_humd_solo(unsigned char ult_hmd_solo, unsigned char ult_pluvio, char comp_rand);

/**
 * Gera o valor de pluviosidade com base no último valor de pluviosidade.
 * O novo valor a gerar será o incremento ao último valor gerado, adicionado de um valor
 * de modificação (positivo ou negativo).
 *
 * O valor de modificação terá uma componente aleatória e uma componente relativa à última
 * temperatura registada, que contribuirá para uma maior ou menor alteração à modificação.
 *
 * Assim produz-se o efeito de, com temperaturas altas ser menos provável que chova, e com
 * temperaturas mais baixas ser mais provável que chova.
 *
 * Quando a pluviosidade anterior for nula, se o valor de modificação for negativo a
 * pluviosidade deverá permanecer nula.
 *
 * @param ult_pluvio Último valor de pluviosidade medido (mm)
 * @param ult_temp Último valor de temperatura medido (°C)
 * @param comp_rand Componente aleatório para a geração do novo valor de pluviosidade
 *
 * @return A nova medição do valor de pluviosidade (mm)
 */
unsigned char sens_pluvio(unsigned char ult_pluvio, char ult_temp, char comp_rand);
#endif
