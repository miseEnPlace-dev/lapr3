#include <stdlib.h>

#include "sensor.h"
#include "shared.h"
#include "readings_generate.h"
#include "random.h"
#include "sensors.h"

Sensor **bootstrap(unsigned int const *n_sensors) {
    Sensor **data = (Sensor **)malloc(sizeof(Sensor *) * NUM_OF_SENSOR_TYPES);

    for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++) {
        Sensor *sensors = (Sensor *)malloc(n_sensors[i] * sizeof(Sensor));
        data[i] = sensors;
    }

    return data;
}

void deallocate(Sensor **data, unsigned int const *n_sensors) {
    for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++) {
        for (int j = 0; j < n_sensors[i]; j++) {
            free(data[i][j].readings);
            free(data[i][j].errors);
        }
        free(data[i]);
    }

    free(data);
}

Sensor bootstrap_temperature(unsigned long frequency, unsigned int id) {
    if (frequency == 0) frequency = TEMPERATURES_SENSOR_INTERVAL;
    Sensor s;
    s.id = id;
    s.name = "temp sens";
    s.sensor_type = TEMPERATURE_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_TEMPERATURE;
    s.min_limit = LOWER_LIMIT_TEMPERATURE;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "ºC";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);

    // generate readings
    generate_temperature(s);

    return s;
}

Sensor bootstrap_wind_vel(unsigned long frequency, unsigned int id) {
    if (frequency == 0) frequency = WIND_VELOCITY_SENSOR_INTERVAL;
    Sensor s;
    s.id = id;
    s.name = "wind veloc sens";
    s.sensor_type = WIND_VELOCITY_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_WIND_VELOCITY;
    s.min_limit = LOWER_LIMIT_WIND_VELOCITY;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "km/h";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);

    // function pointer to the generator function
    unsigned short (*sens_func)(unsigned short, short);
    sens_func = (unsigned short (*)(unsigned short, short)) &sens_velc_vento;

    unsigned short base_value = pcg32_random_r() % VELC_BASE_VALUE;

    // generate readings
    generate_general(s, base_value, sens_func, 1);

    return s;
}

Sensor bootstrap_wind_dir(unsigned long frequency, unsigned int id) {
    if (frequency == 0) frequency = WIND_DIRECTION_SENSOR_INTERVAL;
    Sensor s;
    s.id = id;
    s.name = "wind dir sens";
    s.sensor_type = WIND_DIRECTION_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_WIND_DIRECTION;
    s.min_limit = LOWER_LIMIT_WIND_DIRECTION;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "º";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);

    // function pointer to the generator function
    unsigned short (*sens_func)(unsigned short, short);
    sens_func = (unsigned short (*)(unsigned short, short)) &sens_dir_vento;

    unsigned short base_value = pcg32_random_r() % DIR_BASE_VALUE;

    // generate readings
    generate_general(s, base_value, sens_func, 0);

    return s;
}

Sensor bootstrap_pluvio(unsigned long frequency, unsigned int id, Sensor temp_sensor) {
    if (frequency == 0) frequency = PLUVIO_SENSOR_INTERVAL;
    Sensor s;
    s.id = id;
    s.name = "pluvio sens";
    s.sensor_type = PLUVIO_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_PLUVIO;
    s.min_limit = LOWER_LIMIT_PLUVIO;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "mm";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);

    // function pointer to the generator function
    unsigned short (*sens_func)(unsigned short, unsigned short, short);
    sens_func = (unsigned short (*)(unsigned short, unsigned short, short)) &sens_pluvio;

    unsigned short base_value = pcg32_random_r() % PLUVIO_BASE_VALUE;

    // generate readings
    generate_dependant(s, temp_sensor, base_value, sens_func, 1);

    return s;
}

Sensor bootstrap_soil_humidity(unsigned long frequency, unsigned int id, Sensor pluvio_sensor) {
    if (frequency == 0) frequency = SOIL_HUMIDITY_SENSOR_INTERVAL;
    Sensor s;
    s.id = id;
    s.name = "soil hum sens";
    s.sensor_type = SOIL_HUMIDITY_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_SOIL_HUMIDITY;
    s.min_limit = LOWER_LIMIT_SOIL_HUMIDITY;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "%";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);

    // function pointer to the generator function
    unsigned short (*sens_func)(unsigned short, unsigned short, short);
    sens_func = (unsigned short (*)(unsigned short, unsigned short, short)) &sens_humd_solo;

    unsigned short base_value = SOIL_BASE_VALUE;

    // generate readings
    generate_dependant(s, pluvio_sensor, base_value, sens_func, 0);

    return s;
}

Sensor bootstrap_air_humidity(unsigned long frequency, unsigned int id, Sensor pluvio_sensor) {
    if (frequency == 0) frequency = AIR_HUMIDITY_SENSOR_INTERVAL;
    Sensor s;
    s.id = id;
    s.name = "air hum sens";
    s.sensor_type = AIR_HUMIDITY_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_AIR_HUMIDITY;
    s.min_limit = LOWER_LIMIT_AIR_HUMIDITY;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "%";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);

    // function pointer to the generator function
    unsigned short (*sens_func)(unsigned short, unsigned short, short);
    sens_func = (unsigned short (*)(unsigned short, unsigned short, short)) &sens_humd_atm;

    unsigned short base_value = AIR_BASE_VALUE;

    // generate readings
    generate_dependant(s, pluvio_sensor, base_value, sens_func, 0);

    return s;
}

