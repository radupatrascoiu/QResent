import React, { useState } from "react";
import "../App.css";

const ReadMore3 = ({ children }) => {
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
const Content3 = () => {
return (
	<div className="container">

		<ReadMore3>
		Proiectul a fost realizat în cadrul materiei MPS. Pe lângă dezvoltarea tehnică a studenților, acest prim proiect are că scop formarea unor abilități de a planifică și de ne organiza, de a lucra în echipa, de a împărți sarcinile corespunzător, totul pentru a livra un produs cât mai bun.
		</ReadMore3>

	</div>
);
};



export default Content3;