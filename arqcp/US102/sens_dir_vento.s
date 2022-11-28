.section .text
  .global sens_dir_vento # unsigned char sens_dir_vento(unsigned short ult_dir_vento, short comp_rand)

# rdi ult_dir_vento
# rsi comp_rand

sens_dir_vento:

  movw %si, %ax # ax = random component
  cwtd # cast byte to word

  testw %ax, %ax
  je compRandZero

  movw VELC_SENSOR_DIR_WIND_MAX_VARIATION (%rip), %cx # cw = max variation
  incw %cx # cx = max variation + 1

  idivw %cx # divide random component by max variation (remainder in %dx)

  addw %di, %dx # add to last random value
  movw %dx, %ax

  jmp continue

compRandZero:

  movw %di, %ax # ax = last random value
  jmp end

compRandNeg:

  negw %ax # make positive
  jmp end

continue:
  testw %ax, %ax
  js compRandNeg

end:
ret
