import pandas as pd
import os

# 定义文件路径
excel_path = r'd:\fzu\oop-prgoraming-desigun\oop-curriculum-design\docs\附件.xlsx'
readme_path = r'd:\fzu\oop-prgoraming-desigun\oop-curriculum-design\README.md'

def excel_to_markdown(excel_file):
    """将Excel文件转换为Markdown格式"""
    # 读取Excel文件
    xls = pd.ExcelFile(excel_file)
    
    markdown_content = "\n## 四. 附件\n\n"
    
    # 遍历所有工作表
    for sheet_name in xls.sheet_names:
        df = pd.read_excel(xls, sheet_name)
        
        # 添加工作表名称
        markdown_content += f"### {sheet_name}\n\n"
        
        # 转换为Markdown表格
        markdown_content += df.to_markdown(index=False) + "\n\n"
    
    return markdown_content

def append_to_readme(markdown_content, readme_file):
    """将Markdown内容追加到README文件"""
    with open(readme_file, 'a', encoding='utf-8') as f:
        f.write(markdown_content)

if __name__ == "__main__":
    # 检查Excel文件是否存在
    if not os.path.exists(excel_path):
        print(f"错误：Excel文件 {excel_path} 不存在")
        exit(1)
    
    # 检查README文件是否存在
    if not os.path.exists(readme_path):
        print(f"错误：README文件 {readme_path} 不存在")
        exit(1)
    
    # 转换Excel为Markdown
    print("正在将Excel转换为Markdown...")
    md_content = excel_to_markdown(excel_path)
    
    # 追加到README
    print("正在追加到README.md...")
    append_to_readme(md_content, readme_path)
    
    print("操作完成！")
