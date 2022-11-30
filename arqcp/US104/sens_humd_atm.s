.section .text
  .global sens_humd_atm # char sens_humd_atm(unsigned char ult_hmd_atm, unsigned char ult_pluvio, char comp_rand)

# rdi ult_hmd_atm
# rsi ult_pluvio
# rdx comp_rand

# Limit the max variation using the remainder of division
#
# The random component is divided by the max variation + 1, this way, the ramainder will allways be
# a random value in the interval [-n,n], n = max variation.
sens_humd_atm:
  movb %dl, %al # al = random component
  movb SOIL_HUMD_SENSOR_RAINING_MAX_VARIATION(%rip), %cl # cl = max raining variation

  pushq %rdx # save random component

  cbtw # cast byte to word
  idivb %cl # divide random component by max variation (remainder in %ah)

  shrw $8, %ax # get the value to right position (%al)

  cbtw
  imulb %sil # al = last pluv * (random%max_raining_variation)

  movb PLUV_CONTRIB_HUMD(%rip), %sil
  cbtw
  imulb %sil # al = pluvio contrib

  cmpb $0, %al # if pluv contrib < 0
  jge .C1
  negb %al

  .C1:
    cmpb %cl, %al
    jg .over_limit # if over limit set contrib to max contrib
    negb %cl
    cmpb %cl, %al
    jl .under_limit # if under limit set contrib to min contrib
  .C2:
    movb SOIL_HUMD_SENSOR_MAX_VARIATION(%rip), %cl # cl = max variation of humidity sensor
    incb %cl # cl = max variation + 1

  movb %al, %r8b # r8b = pluv contrib

  popq %rdx # get value of random component
  movb %dl, %al

  cbtw # cast byte to word
  idivb %cl # divide random component by max variation (remainder in %ah)

  shrw $8, %ax # get the value to right position (%al)
  addb %al, %dil # dil = last humidity + random component
  addb %r8b, %dil # dil = last humidity + random component + pluvio contrib

  cmpb $100, %dil # if result > 100
  jg .over_percentage

  cmpb $0, %dil
  jl .zero

  .C3:
    movb %dil, %al

  ret
.over_limit:
  movb %cl, %al
  jmp .C2
.under_limit:
  movb %cl, %al
  jmp .C2
.over_percentage:
  cmpb $0, %al
  jl .module
  .continue:
    movb $100, %dil
    subb %al, %dil
  jmp .C3
.module:
  negb %al
  jmp .continue
.zero:
  movb $0, %dil
  jmp .C3
