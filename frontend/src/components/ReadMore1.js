import React, { useState } from "react";
import "../App.css";

const ReadMore = ({ children }) => {
const text = children;
const [isReadMore, setIsReadMore] = useState(true);
const toggleReadMore = () => {
	setIsReadMore(!isReadMore);
};
return (
	<p className="text">
	{isReadMore ? text.slice(0, 100) : text}
	<span onClick={toggleReadMore} className="read-or-hide">
		{isReadMore ?  "  ...read more"  : " show less"}
	</span>
	</p>
);
};
const Content1 = () => {
return (
	<div className="container">

		<ReadMore>
		Dorind să vină în sprijinul digitalizarii educației,  QResent permite un sistem de gestionat prezența  studenților la cursuri atât în format fizic, cât și în format online. Procesul constă în scanarea unui cod QR în diverse momente ale unui curs.	
		</ReadMore>

	</div>
);
};



export default Content1;