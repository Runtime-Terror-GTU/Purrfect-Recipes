import React, {useRef, useState, useEffect } from 'react';
import Footer from '../HomePage/Footer';
import './profilePage.css';
import Ad from '../../images/ad.png';

import MainNavBar from '../Main/MainNavBar'; //bu degisebilir
import ScrollToTop from '../ScrollToTop';

const ProfilePage= () => {

  const [isOpen, setIsOpen] = useState(false);

  const toggle = () => {
      setIsOpen(!isOpen);
  };
  const formRef = useRef()

  const handleSubmit = (e) =>{
      e.preventDefault()
  }

  return (
    <>
        <ScrollToTop />
        <MainNavBar toggle={toggle} />

        <div className="i">
              <div className="i-left">
                    <div className="i-up-left-wrapper">

                    </div>

                    <div className="i-down-left-wrapper">

                    </div>
              </div>
              
              <div className="i-right">
                  <div className="i-right-wrapper">
                          <img src={Ad} alt="" className="i-img"></img>
                          If you want to put an ad here , contact us.
                          
                  </div>
              </div>
              
        </div>

        <Footer />

   </>
  )
}

export default ProfilePage;