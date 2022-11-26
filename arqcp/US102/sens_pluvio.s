.section .text
  .global sens_pluvio # unsigned char sens_pluvio(unsigned char ult_pluvio, char ult_temp, char comp_rand)

# rdi ult_pluvio
# rsi ult_temp
# rdx comp_rand

sens_pluvio:
  movb %dl, %al # al = random component
  cbtw # cast byte to word

  cmpb HIGH_TEMP_DEFAULT(%rip), %sil # if ult_temp > HIGH_TEMP_DEFAULT
  jge high_temp

  movb PLUVIO_SENSOR_MAX_VARIATION(%rip), %cl # cl = max variation
  jmp continue

high_temp:
    movb PLUVIO_SENSOR_MAX_VARIATION_HIGH_TEMP(%rip), %cl # cl = max variation

continue:
  incb %cl # cl = max variation + 1

  idivb %cl # divide random component by max variation (remainder in %ah)

  sarw $8, %ax # get the value to right position (%al)

  addb %dil, %al # add to last random value
  jmp continue2

pluvioZero:

  movw $0, %ax # ax = 0

  jmp end

compRandNegative:

  testb %dil, %dil # if ult_pluvio == 0
  je pluvioZero

  negw %ax # make positive

  jmp end

continue2:
  testb %al, %al # if random component == 0
  js compRandNegative

end:
  ret
