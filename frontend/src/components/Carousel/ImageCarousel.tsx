import React from 'react';
import Slider from 'react-slick';
import Grid from '@mui/material/Grid2';
import IconButton from "@mui/material/IconButton";

const NextArrow = (props: {
    onClick?: () => void;
}) => {
    const {onClick} = props;
    return (
        <IconButton
            onClick={onClick}
            sx={{
                color: 'text.primary',
                backgroundColor: 'grey.200',
                borderRadius: '50%',
                position: 'absolute', right: "-20px", top: '45%', zIndex: 1,
                ":hover": {
                    backgroundColor: 'grey.300',
                }
            }}
            size={"small"}
        >
            <i className="fa-solid fa-chevron-right"/>
        </IconButton>
    );
};

const PrevArrow = (props: {
    onClick?: () => void;
}) => {
    const {onClick} = props;
    return (
        <IconButton
            onClick={onClick}
            sx={{
                color: 'text.primary',
                backgroundColor: 'grey.200',
                borderRadius: '50%',
                position: 'absolute', left: "-20px", top: '45%', zIndex: 1,
                ":hover": {
                    backgroundColor: 'grey.300',
                }
            }}
            size={"small"}
        >
            <i className="fa-solid fa-chevron-left"/>
        </IconButton>
    );
};

const ImageCarousel = ({images}: {
    images: string[]
}) => {
    const settings = {
        dots: false,
        infinite: images.length > 1,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: images.length > 1,
    };

    return (
        <Grid container justifyContent="center" sx={{padding: '10px 0'}}>
            <Grid size={{
                xs: 12,
                md: 8,
            }}>
                <Slider {...settings}
                        nextArrow={<NextArrow/>}
                        prevArrow={<PrevArrow/>}
                >
                    {images.map((img, index) => (
                        <img
                            key={index}
                            src={img}
                            alt={`slide-${index}`}
                            style={{width: '100%', height: '400px', objectFit: 'cover', borderRadius: '10px'}}
                        />
                    ))}
                </Slider>
            </Grid>
        </Grid>
    );
};

export default ImageCarousel;
