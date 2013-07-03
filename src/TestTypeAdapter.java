import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.example.stardapio.adapter.TypesAdapter;
import com.example.stardapio.bean.ContainerTypeAndSubType;
import com.example.stardapio.bean.SubType;
import com.example.stardapio.bean.Type;

public class TestTypeAdapter {
	public static void main(String[] args) {
		Activity activity = null;
		ContainerTypeAndSubType container = new ContainerTypeAndSubType();
		
		List<Type> types = new ArrayList<Type>();
		List<SubType> subTypes = new ArrayList<SubType>();
		
		Type type1 = new Type();
		type1.setId_restaurant(1);
		type1.setId_type(1);
		type1.setName("Nome1");
		type1.setType("Type1");
		type1.setUrlImage("image1");
		types.add(type1);
		
		Type type2 = new Type();
		type2.setId_restaurant(1);
		type2.setId_type(2);
		type2.setName("Nome2");
		type2.setType("Type2");
		type2.setUrlImage("image2");
		types.add(type2);
		
		SubType subType1 = new SubType();
		subType1.setId_restaurant(1);
		subType1.setId_type(1);
		subType1.setName("SubNome1");
		subType1.setType("SubType1");
		subType1.setidSubType(1);
		subTypes.add(subType1);
		
		SubType subType2 = new SubType();
		subType2.setId_restaurant(1);
		subType2.setId_type(1);
		subType2.setName("SubNome2");
		subType2.setType("SubType2");
		subType2.setidSubType(2);
		subTypes.add(subType2);
		
		SubType subType3 = new SubType();
		subType3.setId_restaurant(1);
		subType3.setId_type(1);
		subType3.setName("SubNome3");
		subType3.setType("SubType3");
		subType3.setidSubType(3);
		subTypes.add(subType3);
		
		SubType subType4 = new SubType();
		subType4.setId_restaurant(1);
		subType4.setId_type(2);
		subType4.setName("SubNome4");
		subType4.setType("SubType4");
		subType4.setidSubType(4);
		subTypes.add(subType4);
		
		container.setTypes(types);
		container.setSubTypes(subTypes);
		
		TypesAdapter adapter = new TypesAdapter(activity, container);
	}
}
