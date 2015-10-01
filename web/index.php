<?php

date_default_timezone_set('Europe/Berlin');

header("Content-type: application/json; charset=utf-8");

//error_reporting(0);
error_reporting(E_ALL);


// default data... to be replaced with database data ;P
$shops = array(
	'UUID' => array(
		'name' => 'THM Shop',
		'location' => 'Wilhelm-Leuschner-Straße 13, 61169 Friedberg', //split up in seperate fields ??
		'image'=>'thm.jpg',
		'products' => array(
			1 => array(
				'name' => 'T-Shirt',
				'price' => 20,
				'productText' => 'Das neue THM Shirt in den Größen S, M, L und XL.',
				'image' => 'mett.jpg'
			),
			2 => array(
				'name' => 'Damenschuh',
				'price' => 40,
				'productText' => 'Schicke Pumps mit dem THM Logo.',
				'image' => 'mett.jpg'
			),
			3 => array(
				'name' => 'Herrenschuh',
				'price' => 38,
				'productText' => 'Schicke Sneaker mit dem THM Logo.',
				'image' => 'mett.jpg'
			),
			4 => array(
				'name' => 'Jacke',
				'price' => 150,
				'productText' => 'Die schwarze THM Jacke mit dem Logo auf der Brust gibt es in den Größen S, M, L und XL. ',
				'image' => 'mett.jpg'
			)
		)
	),
	'b9407f30-f5f8-466e-aff9-25556b57fe6d' => array(
		'name' => 'BSE Shop',
		'location' => 'Wilhelm-Leuschner-Straße 13, 61169 Friedberg', //split up in seperate fields ??
		'image'=>'BSE_1.jpg',
		'products' => array(
			1 => array(
				'name' => 'Mett',
				'price' => 20,
				'productText' => 'Frisches Mett vom Rind oder Schwein mit Zwiebeln.',
				'image' => 'mett.jpg'
			),
			2 => array(
				'name' => 'Hack',
				'price' => 40,
				'productText' => 'Frisches Hack von Rind, Schwein oder gemischt.',
				'image' => 'mince.jpg'
			),
			3 => array(
				'name' => 'Blutwurst',
				'price' => 38,
				'productText' => 'Blutwurst ist eine Kochwurst aus Schweineblut, Speck, Schwarte und Gewürzen.',
				'image' => 'blutwurst.jpg'
			)
		)
	),
	'beac0002-cf4b-9ef1-3e07-fc410a5092d0' => array(
		'name' => 'Laden123',
		'location' => 'Wilhelm-Leuschner-Straße 14, 61169 Friedberg', //split up in seperate fields ??
		'image'=>'BSE_1.jpg',
		'products' => array(
			1 => array(
				'name' => 'Mett',
				'price' => 20,
				'productText' => 'Frisches Mett vom Rind oder Schwein mit Zwiebeln.',
				'image' => 'mett.jpg'
			),
			2 => array(
				'name' => 'Hack',
				'price' => 40,
				'productText' => 'Frisches Hack von Rind, Schwein oder gemischt.',
				'image' => 'mince.jpg'
			),
			3 => array(
				'name' => 'Blutwurst',
				'price' => 38,
				'productText' => 'Blutwurst ist eine Kochwurst aus Schweineblut, Speck, Schwarte und Gewürzen.',
				'image' => 'blutwurst.jpg'
			)
		)
	)
);

//echo $shops['UUID']['location'];

$output = array();
foreach ($_GET as $value) {
	if (isset($shops[$value])) {
		$output[$value]=$shops[$value];
	}
}

echo json_encode($output);


//echo "\r\n\r\n".json_encode($shops);
/*echo "\r\n\r\n";
var_dump(json_decode(json_encode($shops)));*/

?>