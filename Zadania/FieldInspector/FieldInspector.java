import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Vector;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

public class FieldInspector implements FieldInspectorInterface {
	public int publiczne_pole_int;
	public int publiczne_zadeklarowane_pole_int = 1;
	@FieldVersion(version = 0)
	public float publiczne_pole_float;
	
	private int prywatne_pole_int;
	
	private Type getFieldType(String field) {
		System.out.println("TAKI TYP OTRZYMALEM ESSA -> " + field);
		switch (field) {
		case "int":
			return Type.INT;
		case "java.lang.Integer":
			return Type.INT;
		case "long":
			return Type.LONG;
		case "java.lang.Long":
			return Type.LONG;
		case "float":
			return Type.FLOAT;
		case "java.lang.Float":
			return Type.FLOAT;
		case "double":
			return Type.DOUBLE;
		case "java.lang.Double":
			return Type.DOUBLE;
		default:
			return Type.OTHER;	
		}
	}
	
	private Boolean isDeclared(Field f, Field[] declaredFields) {
		for (Field df : declaredFields)
			if (f.equals(df)) return true;
		return false;
	}
	
	@Override
	public Collection<FieldInspectorInterface.FieldInfo> inspect(String className) {
		Collection<FieldInspectorInterface.FieldInfo> result = new Vector<>();
		try {
			Class<?> inspectedClass = Class.forName(className);
			FieldInspectorInterface.FieldInfo fi;
			
			System.out.println("POLA PUBLICZNE");
			Field[] publicFields = inspectedClass.getFields();
			Field[] declaredFields = inspectedClass.getDeclaredFields();
			
			for (Field field : declaredFields) {
				if (!isDeclared(field, publicFields)) continue;
				Type fieldType = getFieldType(field.getType().getName());
				String fieldName = field.getName();
				FieldVersion fieldAnnotation = field.getAnnotation(FieldVersion.class);
				
				if (fieldAnnotation == null)
					fi = new FieldInfo(fieldType, fieldName);
				else {
					int version = fieldAnnotation.version();
					fi = new FieldInfo(fieldType, fieldName, version);
				}
				
				result.add(fi);
			}
			
			return result;
		}
		catch (Exception e) {
			System.out.println("BLAD: " + e.getMessage());
			return null;
		}
	}
}
