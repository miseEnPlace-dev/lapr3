.section .text
  .global sens_pluvio # char sens_pluvio(unsigned char ult_pluvio, char ult_temp, char comp_rand)

# rdi ult_pluvio
# rsi ult_temp
# rdx comp_rand

sens_pluvio:
  movb %dl, %al # al = random component
  cbtw # cast byte to word

  testw %ax, %ax # if random value < 0
  js compRandNegative

continue1:

  cmpb HIGH_TEMP_DEFAULT(%rip), %sil # if ult_temp > HIGH_TEMP_DEFAULT
  jge high_temp

  movb PLUVIO_SENSOR_MAX_VARIATION(%rip), %cl # cl = max variation
  jmp continue2

high_temp:
    movb PLUVIO_SENSOR_MAX_VARIATION_HIGH_TEMP(%rip), %cl # cl = max variation

continue2:
  incb %cl # cl = max variation + 1

  divb %cl # divide random component by max variation (remainder in %ah)

  shrw $8, %ax # get the value to right position (%al)

  addb %dil, %al # add to last random value
  jmp end

pluvioZero:

  movw $0, %ax # ax = 0

  jmp end

compRandNegative:

  testb %dil, %dil # if ult_pluvio == 0
  je pluvioZero

  negw %ax # make positive

  jmp continue1

end:
  ret
