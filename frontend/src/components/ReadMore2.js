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
	{isReadMore ? text.slice(0, 150) : text}
	<span onClick={toggleReadMore} className="read-or-hide">
		{isReadMore ?  "  ...read more"  : " show less"}
	</span>
	</p>
);
};
const Content2 = () => {
return (
	<div className="container">
	
		<ReadMore>
		Managementul Proiectelor Software se studiază în anul IV, pe primul semestru, la specializarea C5 din cadrul Facultăţii de Automatică şi Calculatoare din Universitatea POLITEHNICA din Bucureşti.	
		</ReadMore>
	
	</div>
);
};



export default Content2;

