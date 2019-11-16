
from pprint import pprint

slots_class_text = """\
class {name}:
	__slots__ = {slots}
	def __init__(self, {args}):
		{init}

	def __str__(self):
		return "{name}({str_args})".format({format_args})
"""

def new_slots_class(name, slots):
	if not slots:
		raise ValueError('slots argument can\'t be empty')
	text = slots_class_text.format(
		name = name,
		slots = ','.join(map(repr, slots)),
		args = ','.join(slots),
		init = ','.join('self.{}'.format(name) for name in slots) + ' = ' + ','.join(slots),
		str_args = ', '.join('{}' for i in range(len(slots))),
		format_args = ','.join('self.{}'.format(name) for name in slots),
		)
	g = {}
	exec(text, g)
	return g[name]


possible_operands = [
	('r{}', 	'im{}'),
	('[r64]', 	'im{}'),
	('[im64]', 	'im{}'),
#	('im{}', 	'im{}')

	('r{}',		'r{}'),
	('[r64]',	'r{}'),
	('[im64]', 	'r{}'),
#	('im{}', 	'r{}'),

	('r{}', 	'[r64]'),
	('[r64]', 	'[r64]'),
	('[im64]', 	'[r64]'),
#	('im{}', 	'[r64]'),

	('r{}', 	'[im64]'),
	('[r64]', 	'[im64]'),
#	('[im64]', 	'[im64]'),
#	('im{}', 	'[im64]'),
]

operands_by_type = {
	'm': 	('[r64]',	'[im64]'),
	'r':	('r{}',	),
	'i':	('im{}', ),
}

def find_operand_type(operand):
	for tp, values in operands_by_type.items():
		if operand in values:
			return tp

operands_group = {}

def appned_if_exist(dc, key, value):
	if key in dc:
		dc[key].append(value)
	else:
		dc[key] = [value]

for operand in possible_operands:
	operand_types = tuple(map(find_operand_type, operand))
	appned_if_exist(operands_group, operand_types, operand)

pprint(operands_group)

# mov 	r/m, r/m/i
mov_operands = [
	('r', 	'i'),
	('m', 	'i'),

	('r', 	'r'),
	('m', 	'm'),

	('r',	'm'),
	('m',	'r'),
]


instructions = []

def append_inst(code, opr1, opr2):
	instructions.append({'mnem': code, 'opr1': opr1, 'opr2': opr2})

inst = 'mov'

def append_size_oprands(size, opr1, opr2):
	global inst
	append_inst(inst, 		opr1.format(size), 	opr2.format(size))

def append_range_operands(opr1, opr2):
	for size in (8, 16, 32, 64):
		append_size_oprands(size, opr1, opr2)

append_range_operands('r{}', 	'im{}')
append_range_operands('[r64]', 	'im{}')
append_range_operands('[im64]', 'im{}')

append_range_operands('[r64]', 	'r{}')

append_range_operands('r{}', 	'r{}')



for instruction in instructions:
	print('{0:<{1}}{mnem:<10}{opr1:<10}{opr2:<10}'.format('', 4, **instruction))

