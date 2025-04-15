import {IDataItem} from "./IDataItem";

interface IGraphItem {
    title: string;
    data: IDataItem[];
    serie: { dataKey: string; label: string }[];
}