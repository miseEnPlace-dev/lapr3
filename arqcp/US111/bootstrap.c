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

Sensor bootstrap_temperature() {
    Sensor s;
    s.id = 0;
    s.name = "Temperatura";
    s.sensor_type = TEMPERATURE_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_TEMPERATURE;
    s.min_limit = LOWER_LIMIT_TEMPERATURE;
    s.frequency = TEMPERATURES_SENSOR_INTERVAL;
    s.readings_size = NUM_TEMPERATURE_REGISTERS;
    s.units = "ºC";
    s.readings = (unsigned short *)malloc(sizeof(unsigned short) * s.readings_size);
    s.errors = (unsigned char *)malloc(sizeof(unsigned char) * s.readings_size);
    return s;
}

Sensor bootstrap_wind_vel() {
    Sensor s;
    s.id = 0;
    s.name = "Velocidade do Vento";
    s.sensor_type = WIND_VELOCITY_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_WIND_VELOCITY;
    s.min_limit = LOWER_LIMIT_WIND_VELOCITY;
    s.frequency = WIND_VELOCITY_SENSOR_INTERVAL;
    s.readings_size = NUM_WIND_VELOCITY_REGISTERS;
    s.units = "km/h";
    s.readings = (unsigned short *)malloc(sizeof(unsigned short) * s.readings_size);
    s.errors = (unsigned char *)malloc(sizeof(unsigned char) * s.readings_size);
    return s;
}

Sensor bootstrap_wind_dir() {
    Sensor s;
    s.id = 0;
    s.name = "Direção do Vento";
    s.sensor_type = WIND_DIRECTION_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_WIND_DIRECTION;
    s.min_limit = LOWER_LIMIT_WIND_DIRECTION;
    s.frequency = WIND_DIRECTION_SENSOR_INTERVAL;
    s.readings_size = NUM_WIND_DIRECTION_REGISTERS;
    s.units = "º";
    s.readings = (unsigned short *)malloc(sizeof(unsigned short) * s.readings_size);
    s.errors = (unsigned char *)malloc(sizeof(unsigned char) * s.readings_size);
    return s;
}

Sensor bootstrap_pluvio() {
    Sensor s;
    s.id = 0;
    s.name = "Pluviosidade";
    s.sensor_type = PLUVIO_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_PLUVIO;
    s.min_limit = LOWER_LIMIT_PLUVIO;
    s.frequency = PLUVIO_SENSOR_INTERVAL;
    s.readings_size = NUM_PLUVIO_REGISTERS;
    s.units = "mm";
    s.readings = (unsigned short *)malloc(sizeof(unsigned short) * s.readings_size);
    s.errors = (unsigned char *)malloc(sizeof(unsigned char) * s.readings_size);
    return s;
}

Sensor bootstrap_soil_humidity() {
    Sensor s;
    s.id = 0;
    s.name = "Humidade do Solo";
    s.sensor_type = SOIL_HUMIDITY_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_SOIL_HUMIDITY;
    s.min_limit = LOWER_LIMIT_SOIL_HUMIDITY;
    s.frequency = SOIL_HUMIDITY_SENSOR_INTERVAL;
    s.readings_size = NUM_SOIL_HUMIDITY_REGISTERS;
    s.units = "%";
    s.readings = (unsigned short *)malloc(sizeof(unsigned short) * s.readings_size);
    s.errors = (unsigned char *)malloc(sizeof(unsigned char) * s.readings_size);
    return s;
}

Sensor bootstrap_air_humidity() {
    Sensor s;
    s.id = 0;
    s.name = "Humidade do Ar";
    s.sensor_type = AIR_HUMIDITY_SENSOR_TYPE;
    s.max_limit = UPPER_LIMIT_AIR_HUMIDITY;
    s.min_limit = LOWER_LIMIT_AIR_HUMIDITY;
    s.frequency = AIR_HUMIDITY_SENSOR_INTERVAL;
    s.readings_size = NUM_AIR_HUMIDITY_REGISTERS;
    s.units = "%";
    s.readings = (unsigned short *)malloc(sizeof(unsigned short) * s.readings_size);
    s.errors = (unsigned char *)malloc(sizeof(unsigned char) * s.readings_size);
    return s;
}

