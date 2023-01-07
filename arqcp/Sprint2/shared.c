const char TEMP_SENSOR_MAX_VARIATION = 2;
const char TEMP_BASE_VALUE = 10;
const char TEMP_VARIATION = 20;
const char VELC_SENSOR_MAX_VARIATION = 10; // velocity variation (km/h)
const char VELC_BASE_VALUE = 30;
const short DIR_BASE_VALUE = 360;
const char PLUVIO_BASE_VALUE = 5;
const char SOIL_BASE_VALUE = 10;
const char AIR_BASE_VALUE = 10;
const char PLUV_CONTRIB_HUMD = 8;          // every mm of pluv contributes to humidity (%)
const char SOIL_HUMD_SENSOR_MAX_VARIATION = 3;
const char SOIL_HUMD_SENSOR_RAINING_MAX_VARIATION = 20;
const char VELC_SENSOR_DIR_WIND_MAX_VARIATION = 10;
const char PLUVIO_SENSOR_MAX_VARIATION = 5;
const char HIGH_TEMP_DEFAULT = 25;
const char PLUVIO_SENSOR_MAX_VARIATION_HIGH_TEMP = 2;

const char *SENSOR_TYPE_DESIGNATIONS[] = {
    "Temperatura",
    "Veloc. Vento",
    "Dir. Vento",
    "Pluviosidade",
    "Humid. Solo",
    "Humid. Ar"
};



