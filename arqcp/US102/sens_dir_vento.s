.section .text
  .global sens_dir_vento # char sens_dir_vento(unsigned short ult_dir_vento, short comp_rand)

# rdi ult_dir_vento
# rsi comp_rand

sens_dir_vento:
ret
