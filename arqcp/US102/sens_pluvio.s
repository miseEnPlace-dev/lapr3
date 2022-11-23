.section .text
  .global sens_pluvio # char sens_pluvio(unsigned char ult_pluvio, char ult_temp, char comp_rand)

# rdi ult_pluvio
# rsi ult_temp
# rdx comp_rand

sens_pluvio:
  # -----VERIFIY LAST TEMPERATURE VALUE------
  movb %dl, %al # al = random component

  cmpb HIGH_TEMP_DEFAULT(%rip), %sil # if ult_temp > HIGH_TEMP_DEFAULT
  jge high_temp

  movb PLUVIO_SENSOR_MAX_VARIATION(%rip), %cl # cl = max variation
  jmp continue

  high_temp:
    movb PLUVIO_SENSOR_MAX_VARIATION_HIGH_TEMP(%rip), %cl # cl = max variation

  continue:
    incb %cl # cl = max variation + 1
    cbtw # cast byte to word

  divb %cl # divide random component by max variation (remainder in %ah)

  shrw $8, %ax # get the value to right position (%al)

  #------VERIFY LAST PLUVIO VALUE-----
  testb %dil, %dil # if ult_pluvio = 0
  je pluvioZero

  addb %dil, %al # add to last random value
  jmp end

  pluvioZero:

  cmpb $0, %al # if random value < 0
  js compRandNegative

  addb %dil, %al # add to last random value

  jmp end

  compRandNegative:

  movb $0, %al
  jmp end

  end:
  ret
