

def write(title, *args):
	if title == 'lpar':
		print('{')
	elif title == 'rpar':
		print('}')
	elif title == 'func':
		print('{} {}({})'.format(args[0], args[1], ', '.join(args[2])))
	elif title == 'var':
		print('{} {};')
	else:
		raise ValueError('unknown title: {}'.format(title))


"""
int main(int argc, char **argv)
{
	
}
"""
