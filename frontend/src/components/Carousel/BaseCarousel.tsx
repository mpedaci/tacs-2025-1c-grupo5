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

const BaseCarousel = ({items}: {
    items: React.ReactNode[]
}) => {
    const settings = {
        dots: false,
        infinite: items.length > 1,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: items.length > 1,
    };
    console.log(items);
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
                    {items}
                </Slider>
            </Grid>
        </Grid>
    );
};

export default BaseCarousel;
