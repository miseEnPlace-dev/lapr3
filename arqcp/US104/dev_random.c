#include <stdio.h>
#include <stdint.h>

uint64_t get_value_from_dev_random() {
        uint64_t buffer[1];
        FILE *f;
        int result;
        
        f = fopen("/dev/urandom", "r");

        if (f == NULL) {
                printf("Error: open() failed to open /dev/random for reading\n");
                return 0;
        }

        result = fread(buffer, sizeof(uint64_t), 1, f);

        if (result < 1) {
                printf("Error: failed to read any words\n");
                return 0;
        }

        return buffer[0];
}
