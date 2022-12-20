.section .text
  .global sens_dir_vento # unsigned short sens_dir_vento(unsigned short ult_dir_vento, short comp_rand)

# rdi ult_dir_vento
# rsi comp_rand

sens_dir_vento:

  movw %si, %ax # ax = random component
  cbtw # cast byte to word

  testw %ax, %ax
  je compRandZero

  movb VELC_SENSOR_DIR_WIND_MAX_VARIATION (%rip), %cl # cx = max variation
  incb %cl # cx = max variation + 1

  idivb %cl # divide random component by max variation (remainder in %ah)

  sarw $8, %ax # get the value to right position (%al)

  movsbw %al, %ax # ax = last random

  addw %di, %ax # add to last random value

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
  cwtd # cast word to long
  movw $360, %cx # cx = 360
  idivw %cx # divide by 360 (remainder in %dx)
  movw %dx, %ax # ax = result

ret
