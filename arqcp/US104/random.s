.section .data
	.global state
	.global inc

.section .text
	.global pcg32_random_r

pcg32_random_r:
	# --- prologue ---
	pushq	%rbp			# save rbp value
	movq	%rsp, %rbp		# initialize base pointer


	# --- oldstate = state ---
	movq	state(%rip), %rdi	# copy state's value to rdi (rdi = oldstate)


	# --- state = oldstate * 6364136223846793005ULL + (inc|1) ---
	# oldstate * big number
	movq	%rdi, %rax		# copy oldstate to rax
	movq	$6364136223846793005, %rsi
	mulq	%rsi			# mult the big number and oldstate - result in rax

	# inc | 1
	movq	inc(%rip), %rsi		# copy inc's value to rsi
	orq	$1, %rsi		# rsi = rsi | 1 (rsi = inc)

	# oldstate * big number + (inc | 1)
	addq	%rsi, %rax		# add inc | 1 (rsi) to mult result (rax)

	# state = oldstate * ...
	movq	%rax, state(%rip)	# copy operation's result to state


	# --- xorshifted = ((oldstate >> 18u) ^ oldstate) >> 27u ---
	# oldstate >> 18
	movq	%rdi, %rsi		# copy oldstate to rsi
	shrq	$18, %rsi		# logical right shift rsi by 18

	# (oldstate >> 18) ^ oldstate
	xorq	%rdi, %rsi		# exclusive or (rsi = rsi ^ rdi)

	# (...) >> 27
	shrq	$27, %rsi		# logical right shift rsi by 27 (rsi = xorshifted)


	# --- rot = oldstate >> 58u ---
	movq	%rdi, %rcx		# copy oldstate to rdx
	shrq	$59, %rcx		# logical right shift rdx by 59 (rcx = rot)


	# --- return (xorshifted >> rot) | (xorshifted << ((-rot) & 31)) ---
	# (-rot) & 31
	movl	%ecx, %edx		# copy rot to edx
	negl	%edx			# neg rot (-rot) to edx
	andl	$31, %edx		# and (edx = edx & 31) (edx = (-rot) & 31)

	# xorshifted << (...)
	movl	%esi, %r8d		# copy xorshifted to r8d

	movq	%rcx, %r9		# save rcx content
	movl	%edx, %ecx		# copy edx to ecx (to use cl)

	sall	%cl, %r8d		# left shift xorshifted by [(-rot) & 31)]

	movq	%r9, %rcx		# restore rcx

	# xorshifted >> rot
	shrl	%cl, %esi		# logical right shift xorshifted by rot

	# return (...) | (...)
	movl	%esi, %eax		# copy xorshifted >> rot to eax
	orl	%r8d, %eax		# final or


	# --- epilogue ---
	movq	%rbp, %rsp
	popq	%rbp

	ret
