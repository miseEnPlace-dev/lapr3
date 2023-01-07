#include <stdio.h>
#include <stdlib.h>

#include "sensor.h"
#include "print_result.h"
#include "sensor_summary.h"
#include "export_to_csv.h"
#include "bootstrap.h"
#include "shared.h"
#include "dynamic_sensors.h"

void flush_stdin();

void option1(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    print_readings(data, n_sensors);
}

void option2(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    print_small(data, n_sensors);
}

void option3(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    print_summary(data, n_sensors);
}

int get_sensor_type();

void option4(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    // add sensor
    int sensor_type = get_sensor_type();
    Sensor new_sensor;
    switch(sensor_type) {
        case TEMPERATURE_SENSOR_TYPE:
            new_sensor = bootstrap_temperature(0, ++*count);
            break;
        case WIND_VELOCITY_SENSOR_TYPE:
            new_sensor = bootstrap_wind_vel(0, ++*count);
            break;
        case WIND_DIRECTION_SENSOR_TYPE:
            new_sensor = bootstrap_wind_dir(0, ++*count);
            break;
        case PLUVIO_SENSOR_TYPE: {
            Sensor temp_sensor = data[TEMPERATURE_SENSOR_TYPE][0]; // depends on a temperature sensor
            new_sensor = bootstrap_pluvio(0, ++*count, temp_sensor);
            break;
        }
        case SOIL_HUMIDITY_SENSOR_TYPE: {
            Sensor pluvio_sensor = data[PLUVIO_SENSOR_TYPE][0]; // depends on a pluvio sensor
            new_sensor = bootstrap_soil_humidity(0, ++*count, pluvio_sensor);
            break;
        }
        case AIR_HUMIDITY_SENSOR_TYPE: {
            Sensor pluvio_sensor = data[PLUVIO_SENSOR_TYPE][0]; // depends on a pluvio sensor
            new_sensor = bootstrap_air_humidity(0, ++*count, pluvio_sensor);
            break;
        }
    }

    add_sensor(new_sensor, data, n_sensors);
    printf("Adicionado 1 sensor do tipo %s.\n", SENSOR_TYPE_DESIGNATIONS[sensor_type]);
}

Sensor *find_sensor_by_id(unsigned short id, Sensor **data, unsigned int const *n_sensors);

void option5(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    // delete sensor
    int choice;
    printf("Insira o ID do sensor que pretende remover: ");
    scanf("%d", &choice);
    Sensor *p_delete = find_sensor_by_id((unsigned short)choice, data, n_sensors);

    if (p_delete == NULL) {
        printf("Não existe nenhum sensor com o id %d.\n", choice);
        return;
    }

    unsigned short sensor_type = p_delete->sensor_type;
    unsigned int n_sensors_chosen_type = n_sensors[sensor_type];
    if ((sensor_type == TEMPERATURE_SENSOR_TYPE || sensor_type == PLUVIO_SENSOR_TYPE) && n_sensors_chosen_type <= 1) {
        printf("Não é possível remover mais sensores do tipo %s.\n", SENSOR_TYPE_DESIGNATIONS[sensor_type]);
        return;
    }

    remove_sensor(p_delete, data, n_sensors);
    printf("Removido sensor com o id %d.\n", choice);
}

void option6(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    // adjust freq
    int choice;
    printf("Insira o ID do sensor que pretende: ");
    scanf("%d", &choice);
    Sensor *p_sens = find_sensor_by_id((unsigned short)choice, data, n_sensors);

    if (p_sens == NULL) {
        printf("Não existe nenhum sensor com o id %d.\n", choice);
        return;
    }

    printf("\nSensor do tipo %s c/ id %hu.\n", SENSOR_TYPE_DESIGNATIONS[p_sens->sensor_type], p_sens->id);
    printf("Nº de leituras do sensor: %lu leituras\n", p_sens->readings_size);
    printf("Frequência atual do sensor: %lu segundos\n", p_sens->frequency);
    printf("\nInsira a nova frequência: ");
    long input;
    scanf("%ld", &input);
    
    if (input <= 0) {
        printf("Frequência inserida inválida.\n");
        return;
    }

    unsigned long new_freq = (unsigned long)input;
    adjust_sensor_freq(p_sens, new_freq);
    printf("Ajustada a frequência do sensor c/ id %hu para %lu segundos.\n", p_sens->id, new_freq);
}

void option7(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    export_result(data, n_sensors);
    export_summary(data, n_sensors);
    printf("Dados exportados com sucesso!\n");
}

void quit(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    deallocate(data, n_sensors);
    exit(0);
}

void init_ui(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    do {
        printf("\nMenu:\n");
        printf(" 1. Mostrar leituras\n");
        printf(" 2. Mostrar detalhes dos sensores\n");
        printf(" 3. Apresentar matriz de resumo\n");
        printf(" 4. Adicionar sensor\n");
        printf(" 5. Remover sensor\n");
        printf(" 6. Alterar frequência de um sensor\n");
        printf(" 7. Exportar dados\n");
        printf(" 0. Sair\n");
    
        printf("\nSelecione uma opção: ");
        int choice;
        scanf("%d", &choice);
        printf("\n");
    
        void (*options[])() = {quit, option1, option2, option3, option4, option5, option6, option7};
        if (choice >= 0 && choice <= 7) {
            options[choice](data, n_sensors, count);
        } else {
            printf("Opção inválida.\n");
        }
        printf("\nPressione ENTER para continuar... ");
        flush_stdin(choice);
        getchar();
    } while(1);
}

void get_config(char *filename) {
    printf("Insira o nome do ficheiro de configuração: ");
    scanf("%99s", filename);
    printf("\n");
}

int get_sensor_type() {
    int choice;
    do {
        printf("Tipos de sensores:\n");
        for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++) {
            printf(" %d. %s\n", i + 1, SENSOR_TYPE_DESIGNATIONS[i]);
        }
        printf(" 0. Voltar\n");

        printf("\nSelecione o tipo de sensor que pretende: ");
        scanf("%d", &choice);
        if (choice < 0 || choice > NUM_OF_SENSOR_TYPES) {
            printf("Opção inválida.\n\nPressione ENTER para continuar... ");
            flush_stdin(choice);
            getchar();
            printf("\n");
        }
    } while(choice < 0 || choice > NUM_OF_SENSOR_TYPES);

    return choice - 1;
}

Sensor *find_sensor_by_id(unsigned short id, Sensor **data, unsigned int const *n_sensors) {
    for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++) {
        for (int j = 0; j < n_sensors[i]; j++) {
            if (data[i][j].id == id) return &data[i][j];
        }
    }
    return NULL;
}

void flush_stdin(int c) {
    while ((c = getchar()) != '\n' && c != EOF); // flush the stdin else getchar will be skipped
}

