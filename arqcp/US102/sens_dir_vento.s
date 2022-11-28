.section .text
  .global sens_dir_vento # unsigned word sens_dir_vento(unsigned short ult_dir_vento, short comp_rand)

# rdi ult_dir_vento
# rsi comp_rand

sens_dir_vento:

  movw %si, %ax # ax = random component

  testw %ax, %ax
  je compRandZero

  movw VELC_SENSOR_DIR_WIND_MAX_VARIATION (%rip), %cx # cx = max variation
  incw %cx # cx = max variation + 1

  cwtl # cast byte to word
  idivw %cx # divide random component by max variation (remainder in %dx)

  addw %di, %dx # add to last random value

  jmp continue

compRandZero:

  movw %di, %ax # al = last random value
  jmp end

compRandNeg:

  negw %dx # make positive
  jmp end

continue:
  testw %ax, %ax
  js compRandNeg

end:
ret
