#include <stdio.h>

void set_sensor_summary_register(int **ptr, int num_sensors, int num_registers, int *res){
    int sum = 0; 
    int count = 0;
    int max = 0xFFFFFFFF; 
    int min = 0x7FFFFFFF;
    for(int i = 0; i<num_sensors; i++){
        int *ptr2 = (ptr + i);
        printf("-%d-\n", i);
        for(int j = 0; j<num_registers; j++){
            printf("%d\n", *(ptr2 + j));
            sum += *(ptr2 + j);
            count++;
            if(*(ptr2 + j) < min)
                min = *(ptr2 + j);
            if(*(ptr2 + j) > max)
                max = *(ptr2 + j);
        }
    }
    *res = sum/count;
    *(res + 1) =  min;
    *(res + 2) =  max;
}
