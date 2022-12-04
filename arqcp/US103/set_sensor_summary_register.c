void set_sensor_summary_register(int **ptr, int num_sensors, int num_registers, double *res){
    int sum = 0; 
    int count = 0;
    int max = **ptr; 
    int min = **ptr;
    for(int i = 0; i<num_sensors; i++){
        int *ptr2 = *(ptr + i);
        for(int j = 0; j<num_registers; j++){
            sum += *(ptr2 + j);
            count++;
            if(*(ptr2 + j) < min)
                min = *(ptr2 + j);
            if(*(ptr2 + j) > max)
                max = *(ptr2 + j);
        }
    }
    *res = (double)sum/(double)count;
    *(res + 1) = (double) min;
    *(res + 2) = (double) max;
}
