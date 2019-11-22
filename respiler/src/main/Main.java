package main;

import java.io.IOException;

import tools.BytesTools;
import tools.exceptions.BufferFullException;
import tools.exceptions.InvalidByteCodeException;
import tools.machines.LittleMachine;
import tools.types.Buffer;

public class Main 
{
	public static final boolean SAFE = true;
	
	public static void main(String[] args) throws InvalidByteCodeException, BufferFullException, IOException
	{
		Buffer buffer = new Buffer(100);
		
		buffer.append(2, LittleMachine.INST_COPY_R64_IM64);		// 2
		buffer.append(1, (10 & LittleMachine.REG_NUM_MASK));	// 1
		buffer.append(8, 123);									// 8
		
		buffer.append(2, LittleMachine.INST_EXIT);				// 2
		
		BytesTools.dumpHex(buffer.getBytes(), System.out);
		
		LittleMachine machine = new LittleMachine(buffer.getBytes(), 0);
		machine.run();
		
//		System.out.println(BytesTools.read(machine.getBuffer(), 10 * 8, 8));
	}
}
