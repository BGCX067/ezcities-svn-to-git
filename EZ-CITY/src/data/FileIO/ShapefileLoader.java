package data.FileIO;

import java.util.ArrayList;
import java.util.HashMap;

import simulation.obj.Building;
import simulation.obj.LandCell;

import data.dataManeger.Scene;
import diewald_shapeFile.files.dbf.DBF_Field;
import diewald_shapeFile.files.shp.shapeTypes.ShpPoint;
import diewald_shapeFile.files.shp.shapeTypes.ShpPolygon;
import diewald_shapeFile.files.shp.shapeTypes.ShpShape;
import diewald_shapeFile.shapeFile.ShapeFile;
import draw.Geometry.Poly2DEx;

public class ShapefileLoader {

	private Scene scene = null;

	public ShapeFile m_buildingShapefile = null;
	public ShapeFile m_masterplanShapefile = null;

	public void setScene(Scene _s) {
		scene = _s;
	}

	public ShapefileLoader() {
	}

	// LOAD SHAPE FILE (.shp, .shx, .dbf)

	// loadBuildingShape
	public void loadBuildingShape(String path, String filename)
			throws Exception {
		if (m_buildingShapefile == null) {
			m_buildingShapefile = new ShapeFile(path, filename).READ();

		}
		// TEST: printing some content
		ShpShape.Type shape_type = m_buildingShapefile.getSHP_shapeType();
		System.out.println("\nshape_type = " + shape_type);

		int number_of_shapes = m_buildingShapefile.getSHP_shapeCount();
		int number_of_fields = m_buildingShapefile.getDBF_fieldCount();

		for (int i = 0; i < number_of_shapes; i++) {

			ShpPolygon shape = m_buildingShapefile.getSHP_shape(i);
			String[] shape_info = m_buildingShapefile.getDBF_record(i);

			ShpShape.Type type = shape.getShapeType();
			int number_of_vertices = shape.getNumberOfPoints();
			int number_of_polygons = shape.getNumberOfParts();
			int record_number = shape.getRecordNumber();

			System.out.printf("\nSHAPE[%2d] - %s\n", i, type);
			System.out
					.printf("  (shape-info) record_number = %3d; vertices = %6d; polygons = %2d\n",
							record_number, number_of_vertices,
							number_of_polygons);

			Building b = new Building();
		//	b.setHeight((float) (Math.random() / 20));
			if(Math.random()>0.5)
			{
				b.setHeight(0.02f);
			}else{
				b.setHeight(0.05f);
			}
			
			b.setId(record_number);

			Poly2DEx p = new Poly2DEx();

			double[][] ps = shape.getPoints();

			for (int index = 0; index < number_of_vertices; index++) {

				double orix = ps[index][0];
				double oriy = ps[index][1];

				p.addPoint((float) orix, (float) oriy, 0);
				scene.bounds.add(orix, oriy, 0);

			}

			b.setFootprint(p);

			scene.buildings.add(b);

			System.out.printf("\n");
		}
	}

	// loadBuildingShape
	public void loadMasterPlanShape(String path, String filename)
			throws Exception {
		if (m_masterplanShapefile == null) {
			m_masterplanShapefile = new ShapeFile(path, filename).READ();

		}

		ShpShape.Type shape_type = m_masterplanShapefile.getSHP_shapeType();
		System.out.println("\nshape_type = " + shape_type);

		int number_of_shapes = m_masterplanShapefile.getSHP_shapeCount();
		int number_of_fields = m_masterplanShapefile.getDBF_fieldCount();
		

		for (int i = 0; i < number_of_shapes; i++) {

			ShpPoint shape = m_masterplanShapefile.getSHP_shape(i);
			String[] shape_info = m_masterplanShapefile.getDBF_record(i);

			double[] ps = shape.getPoint();

			LandCell c = new LandCell();
			c.setid(shape.getRecordNumber());

			c.setx((float) ps[0]);
			c.setx((float) ps[1]);

			// c.setArea(larea);
			c.setHeight(1 + "");
			c.setUse(shape_info[1]);
			
			scene.bounds.add(ps[0], ps[1], 0);

			scene.cells.add(c);

		}
	}

}
