#include <stdio.h>
#include <stdlib.h>

#include "sensor.h"
#include "print_result.h"
#include "sensor_summary.h"
#include "export_to_csv.h"
#include "bootstrap.h"
#include "shared.h"
#include "dynamic_sensors.h"

void option1(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    print_readings(data, n_sensors);
}

void option2(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    print_small(data, n_sensors);
}

void option3(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    print_summary(data, n_sensors);
}

void option4(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    // add sensor
    Sensor new_sensor = bootstrap_temperature(TEMPERATURES_SENSOR_INTERVAL, ++(*count));
    add_sensor(new_sensor, data, n_sensors);
    printf("Adicionado 1 sensor do tipo %s.\n", SENSOR_TYPE_DESIGNATIONS[new_sensor.sensor_type]);
}

void option5(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    // delete sensor
    unsigned int n_temp_sensors = n_sensors[TEMPERATURE_SENSOR_TYPE];
    if (n_temp_sensors <= 1) {
        printf("Não é possível remover mais sensores.\n");
        return;
    }
    Sensor *p_delete = &data[TEMPERATURE_SENSOR_TYPE][n_temp_sensors - 1];
    unsigned short deleted_id = p_delete->id;
    remove_sensor(p_delete, data, n_sensors);
    printf("Removido sensor com o id %hu.\n", deleted_id);
}

void option6(Sensor **data, unsigned int *n_sensors, unsigned int *count) {
    // adjust freq
    Sensor *p_sens = &data[WIND_DIRECTION_SENSOR_TYPE][0];
    unsigned long new_freq = p_sens->frequency * 2;
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
        while ((choice = getchar()) != '\n' && choice != EOF); // flush the stdin else getchar will be skipped
        getchar();
    } while(1);
}

void get_config(char *filename) {
    printf("Insira o nome do ficheiro de configuração: ");
    scanf("%99s", filename);
    printf("\n");
}

