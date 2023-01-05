#include "sensor.h"
#include "shared.h"
#include "stdlib.h"

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

Sensor bootstrap_temperature(unsigned long frequency) {
    if (frequency == 0) frequency = TEMPERATURES_SENSOR_INTERVAL;
    Sensor s;
    s.id = 0;
    s.name = "Temperatura";
    s.sensor_type = TEMPERATURE_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_TEMPERATURE;
    s.min_limit = LOWER_LIMIT_TEMPERATURE;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "ºC";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);
    return s;
}

Sensor bootstrap_wind_vel(unsigned long frequency) {
    if (frequency == 0) frequency = WIND_VELOCITY_SENSOR_INTERVAL;
    Sensor s;
    s.id = 0;
    s.name = "Velocidade do Vento";
    s.sensor_type = WIND_VELOCITY_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_WIND_VELOCITY;
    s.min_limit = LOWER_LIMIT_WIND_VELOCITY;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "km/h";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);
    return s;
}

Sensor bootstrap_wind_dir(unsigned long frequency) {
    if (frequency == 0) frequency = WIND_DIRECTION_SENSOR_INTERVAL;
    Sensor s;
    s.id = 0;
    s.name = "Direção do Vento";
    s.sensor_type = WIND_DIRECTION_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_WIND_DIRECTION;
    s.min_limit = LOWER_LIMIT_WIND_DIRECTION;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "º";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);
    return s;
}

Sensor bootstrap_pluvio(unsigned long frequency) {
    if (frequency == 0) frequency = PLUVIO_SENSOR_INTERVAL;
    Sensor s;
    s.id = 0;
    s.name = "Pluviosidade";
    s.sensor_type = PLUVIO_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_PLUVIO;
    s.min_limit = LOWER_LIMIT_PLUVIO;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "mm";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);
    return s;
}

Sensor bootstrap_soil_humidity(unsigned long frequency) {
    if (frequency == 0) frequency = SOIL_HUMIDITY_SENSOR_INTERVAL;
    Sensor s;
    s.id = 0;
    s.name = "Humidade do Solo";
    s.sensor_type = SOIL_HUMIDITY_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_SOIL_HUMIDITY;
    s.min_limit = LOWER_LIMIT_SOIL_HUMIDITY;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "%";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);
    return s;
}

Sensor bootstrap_air_humidity(unsigned long frequency) {
    if (frequency == 0) frequency = AIR_HUMIDITY_SENSOR_INTERVAL;
    Sensor s;
    s.id = 0;
    s.name = "Humidade do Ar";
    s.sensor_type = AIR_HUMIDITY_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_AIR_HUMIDITY;
    s.min_limit = LOWER_LIMIT_AIR_HUMIDITY;
    s.frequency = frequency;
    s.readings_size = SECS_IN_DAY / frequency;
    s.units = "%";
    s.readings = (unsigned short *)calloc(sizeof(unsigned short), s.readings_size);
    s.errors = (unsigned char *)calloc(sizeof(unsigned char), s.readings_size);
    return s;
}

