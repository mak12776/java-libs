
package _olds.machines.machine;

public enum ErrorType
{
	// null pointers
	
	NULL_INSTRUCTION_POINTER,
	NULL_DATA_POINTER,
	NULL_TEMP_POINTER,
	
	// check bip & ip
	
	INVALID_BASE_INSTRUCTION_POINTER, 
	INVALID_INSTRUCTION_POINTER,
	
	// check PI & BI
	
	INVALID_POINTERS_INDEX, 
	INVALID_BUFFERS_INDEX,
	
	// check Data Buffer Index
	
	INVALID_DATA_BUFFER_INDEX,
	INVALID_TEMP_BUFFER_INDEX,
	
	// unknown instruction
	
	UNKNOWN_INSTRUCTION,
}
