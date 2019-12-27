package pxld;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class Animation 
{
	public Image image;
	
	abstract public void start();
	abstract public boolean step();
	
	public void Render(DataOutputStream stream) throws IOException
	{
		byte colorByteCode = image.getColorMode().getByteCode();
		
		stream.write(new byte[] {'a', 'n', 'm', '\n'});
		stream.writeInt(image.width());
		stream.writeInt(image.height());
		stream.writeByte(colorByteCode);
		
		start();
		stream.write(image.getData());
		
		while (step())
		{
			stream.write(image.getData());
		}
	}
	
	public void Render(String name) throws IOException
	{
		Render(new DataOutputStream(new FileOutputStream(name)));
	}
	
	public boolean tryRender(String name)
	{
		try
		{
			Render(name);
		}
		catch (IOException e) 
		{
			return false;
		}
		return true;
	}
}
